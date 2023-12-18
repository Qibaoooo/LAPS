import React, {useState} from 'react'
import { Badge, Button } from "react-bootstrap";
import {cancelLeave} from "../utils/api/apiStaff";

function CreateUpdateAndCancelButtons({leaveapplication}){
    const status = leaveapplication.status;
    const toDate = Date.parse(leaveapplication.toDate);
    const fromDate = Date.parse(leaveapplication.fromDate);
    const currentDate = Date.now();

    const {isCancelled, setIsCancelled} = useState(false);

    const handleCancelCalls = async() => {
        try{
        const response = await cancelLeave(leaveapplication.id.toString());
        console.log(response);
        if (response === 204){
            setIsCancelled(true);
        }
        }
        catch (e){
        console.log(e);
        }
    };

    if (status == "APPROVED" && ( currentDate > toDate && currentDate > toDate)){
        return (
            <React.Fragment>
                <td>
                    <Badge>{leaveapplication.status}</Badge>
                </td>
                <td>   
                    <Badge bg="warning" size="sm">
                        Not Applicable
                    </Badge>
                </td>
                <td>
                    <Badge bg="warning" size="sm">
                        Not Applicable
                    </Badge>
                </td>
            </React.Fragment>
        );
    }
    else if (status == "APPROVED" && !( currentDate > toDate && currentDate > toDate)){
        return (
            <React.Fragment>
                <td>
                    <Badge>{leaveapplication.status}</Badge>
                </td>
                <td>   
                    <Badge bg="warning" size="sm">
                        Not Applicable
                    </Badge>
                </td>
                <td>
                {isCancelled ? (
                    <Badge bg="warning" size="sm">Not Applicable</Badge>
                    ) : (
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
                    <Badge>{leaveapplication.status}</Badge>
                </td>
                <td>   
                    <Button variant="secondary" size="sm">
                        Update
                    </Button>
                </td>
                <td>
                {isCancelled ? (
                    <Badge bg="warning" size="sm">Not Applicable</Badge>
                    ) : (
                    <Button variant="danger" size="sm" onClick={handleCancelCalls}>Cancel</Button>
                    )
                }
                </td>
            </React.Fragment>
        );
    }
}

export default CreateUpdateAndCancelButtons;