import React, {useState, useEffect } from 'react'
import { Badge, Button } from "react-bootstrap";
import {cancelLeave,deleteLeave} from "../utils/api/apiStaff";

function CreateUpdateAndCancelButtons({leaveapplication, onCancel, onDelete }){
    const status = leaveapplication.status;
    const toDate = Date.parse(leaveapplication.toDate);
    const fromDate = Date.parse(leaveapplication.fromDate);
    const currentDate = Date.now();

    const [isCancelled, setIsCancelled] = useState(false);
    const [isDeleted, setIsDeleted] = useState(false);

    useEffect(() => {
        const cancelled = localStorage.getItem(`cancelled-${leaveapplication.id}`);
        if (cancelled === 'true') {
            setIsCancelled(true);
        }
    }, [leaveapplication.id]);


    const handleCancelCalls = async() => {
        const isConfirmed = window.confirm("Are you sure you want to cancel this leave application?");
        if(isConfirmed){
        try{
            const response = await cancelLeave(leaveapplication.id.toString());
            console.log(response);
            if (response.status === 200){
                setIsCancelled(true);
                localStorage.setItem(`cancelled-${leaveapplication.id}`, 'true');
                onCancel();
            }
        }
        catch (e){
            console.log(e);
        }
    }
    };

    const handleDeleteCalls = async () => {
        const isConfirmed = window.confirm("Are you sure you want to delete this leave application?");
        if (isConfirmed) {
          try{
            const response = await deleteLeave(leaveapplication.id.toString(),"delete");
            if (response.status === 200){
                onDelete(); // Calling the onDelete function passed from the parent

            }
          }
          catch(e){
            console.log(e);
          }
        }
    };


    if (status === "APPROVED" && ( currentDate > fromDate && currentDate > toDate) ||  status === "CANCELLED"){
        return (
            <React.Fragment>
                <td>
                    <Badge bg="warning" size="sm">Not Applicable</Badge>
                </td>
            </React.Fragment>
        );
    }
    else if (status === "APPROVED" && !( currentDate > fromDate && currentDate > toDate)){
        return (
            <React.Fragment>
                <td>
                    {isCancelled ? (<Badge bg="warning" size="sm">Not Applicable</Badge>) : (
                        <Button variant="danger" size="sm" onClick={handleCancelCalls}>Cancel</Button>
                        )
                    }
                </td>
            </React.Fragment>
        );
    }
    else{
        return (
            <React.Fragment>
                <td>
                {isCancelled ? (null) : (
                        <Button
                        style={{marginRight: '10px'}}
                        variant="info"
                        size="sm"
                        onClick={() => {
                        window.location.href =
                            "/staff/leave/edit/?id=" + leaveapplication.id;
                        }}>
                            Update
                      </Button>
                    )
                }  
                {isCancelled ? (<Badge bg="warning" size="sm">Not Applicable</Badge>) : (
                    <>
                        <Button variant="danger" size="sm" style={{marginRight: '10px'}} onClick={handleCancelCalls}>Cancel</Button>
                        <Button variant="danger" size="sm" onClick={handleDeleteCalls}>Delete</Button>
                    </>
                    )
                }
                
                </td>
            </React.Fragment>
        );
    }
}

export default CreateUpdateAndCancelButtons;