var path = require('path')
var express = require('express')
var app = express()

users = [ "test:securePassword" ];


function encrypt(key){
    return key*5;
}

function auth(user,pass){
    console.log("auth: "+user+":"+pass)
    console.log(users)
    login = user+":"+pass
    for(e in users){
        console.log("trying: "+users[e])
        if(users[e] == login){
            console.log("sucess")
            return true;
        }
    }
    return false
}

app.get('/:user/:pass/:key', (req,res) => {
    console.log("Request recieved")
    if(auth(req.params["user"],req.params["pass"])){
        res.send(""+encrypt(req.params["key"]))
    } else {
        res.send("Unauthorized, access denied")
    }
})

app.listen(2000, () => {console.log("app running on port 2000")})