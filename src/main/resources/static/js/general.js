function closeWindowAndGoto(location) {
    let newWindow=window.open('_blank'); // 先打开页面
    window.location.href="about:blank";
    window.close();
    newWindow.location=location; // 后更改页面地址
}