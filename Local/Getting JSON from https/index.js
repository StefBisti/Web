const getData = (callback, url) => {
    const request = new XMLHttpRequest();

    request.addEventListener("readystatechange", () => {
        if(request.readyState === 4 && request.status === 200){
            const data = JSON.parse(request.responseText);
            callback(undefined, data);
        }else if(request.readyState === 4){
            callback("could not fetch data", undefined);
        }
    });

    request.open("GET", url);
    request.send();
}

var a = undefined;

getData((err, data) => {
    console.log("callback");
    if(err){
        console.log(err);
    } else {
        console.log(data);
        a = data;
    }
}, 'https://jsonplaceholder.typicode.com/todos/');