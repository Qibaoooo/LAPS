let setUserinfoLocal = (info) => {
    localStorage.setItem('userinfo', JSON.stringify(info))
}

let getUserinfoFromLocal = () => {
    return JSON.parse(localStorage.getItem('userinfo'))
}

export { setUserinfoLocal, getUserinfoFromLocal }