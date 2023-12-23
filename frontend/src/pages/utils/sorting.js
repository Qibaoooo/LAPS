import React from 'react'

const sortByOvertimeDate = (a,b) => {
    return ('' + a.overtimeDate).localeCompare(b.overtimeDate);
}

const sortByFromDate = (a,b) => {
    return ('' + a.fromDate).localeCompare(b.fromDate);
}

const sortHolidays = (a,b) => {
    return ('' + a[0]).localeCompare(b[0]);
}

export { sortByOvertimeDate, sortByFromDate, sortHolidays } 