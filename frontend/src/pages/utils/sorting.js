import React from 'react'

const sortByOvertimeDate = (a,b) => {
    return ('' + a.overtimeDate).localeCompare(b.overtimeDate);
}

const sortByFromDate = (a,b) => {
    return ('' + a.fromDate).localeCompare(b.fromDate);
}

export { sortByOvertimeDate, sortByFromDate } 