var today = new Date();
console.log(today.getMinutes())

const minutesArrow = document.getElementById("minuteArrow");
minutesArrow.style.transform = `rotate(${today.getMinutes() * 6 + 180}deg)`

const hourArrow = document.getElementById("hourArrow");
hourArrow.style.transform = `rotate(${1 * 30 + 180}deg)`