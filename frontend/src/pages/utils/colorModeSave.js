let saveColorMode = (c) => {
    localStorage.setItem('colorMode', c)
}

let getColorMode = () => {
    let fromLocal = localStorage.getItem('colorMode')
    return fromLocal
}

export { saveColorMode, getColorMode }