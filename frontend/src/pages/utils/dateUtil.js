export const checkIfWeekend = (d) => {
    let date = new Date(d);
    if (date.getDay() == 6 || date.getDay() == 0) {
      return true;
    }
    return false;
  };