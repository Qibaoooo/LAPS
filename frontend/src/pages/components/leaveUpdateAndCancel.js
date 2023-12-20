import React, {useState, useEffect } from 'react'
import { Badge, Button } from "react-bootstrap";
import {cancelLeave} from "../utils/api/apiStaff";

function CreateUpdateAndCancelButtons({leaveapplication, onCancel}){
    const status = leaveapplication.status;
    const toDate = Date.parse(leaveapplication.toDate);
    const fromDate = Date.parse(leaveapplication.fromDate);
    const currentDate = Date.now();

    const [isCancelled, setIsCancelled] = useState(false);
    

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
                        variant="secondary"
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
                    <Button variant="danger" size="sm" onClick={handleCancelCalls}>Cancel</Button>
                    )
                }
                </td>
            </React.Fragment>
        );
    }
}

export default CreateUpdateAndCancelButtons;