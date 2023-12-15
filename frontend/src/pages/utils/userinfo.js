let setUserinfo = (info) => {
    localStorage.setItem('userinfo', JSON.stringify(info))
}

let getUserinfo = () => {
    let fromLocal = JSON.parse(localStorage.getItem('userinfo'))
    return fromLocal
}

export { setUserinfo, getUserinfo }