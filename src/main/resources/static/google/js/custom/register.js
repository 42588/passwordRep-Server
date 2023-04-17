const chat = document.getElementById("chat");
const userInput = document.getElementById("userInput");

let step = "userName";
let userName, password, confirmPassword, email;
const backToLoginButton = document.getElementById("backToLogin");

backToLoginButton.addEventListener("click", function () {
  window.location.href = "login.html";
});

userInput.addEventListener("keydown", function (event) {
  if (event.key === "Enter") {
    event.preventDefault();

    const userMessage = document.createElement("div");
    userMessage.className = "message user";
    userMessage.textContent = userInput.value;
    chat.appendChild(userMessage);

    switch (step) {
      case "userName":
        userName = userInput.value;
        checkuserNameAvailability(userName);
        break;
      case "password":
        password = userInput.value;
        askForConfirmPassword();
        break;
      case "confirmPassword":
        confirmPassword = userInput.value;
        if (confirmPassword === password) {
          askForEmail();
        } else {
          showPasswordMismatch();
        }
        break;
      case "email":
        email = userInput.value;
        registerUser(email, password, userName);
        break;
    }

    userInput.value = "";
  }
});

function askForPassword() {
  const botMessage = document.createElement("div");
  botMessage.className = "message bot";
  botMessage.textContent = "请设置您的密码：";
  chat.appendChild(botMessage);
  step = "password";
}

function askForConfirmPassword() {
  const botMessage = document.createElement("div");
  botMessage.className = "message bot";
  botMessage.textContent = "请再次输入您的密码以确认：";
  chat.appendChild(botMessage);
  step = "confirmPassword";
}

function showPasswordMismatch() {
  const botMessage = document.createElement("div");
  botMessage.className = "message bot";
  botMessage.textContent = "两次输入的密码不一致，请重新设置您的密码：";
  chat.appendChild(botMessage);
  step = "password";
}

function askForEmail() {
  const botMessage = document.createElement("div");
  botMessage.className = "message bot";
  botMessage.textContent = "请输入您的电子邮箱：";
  chat.appendChild(botMessage);
  step = "email";
}

function registerUser(mail, password, userName) {
  // 在这里将用户提供的信息发送给服务器以完成注册过程。
  // 设置服务器端验证接口地址
  const url = "http://localhost:8080/regist";

  // 发送用户名和密码到服务器端验证接口
  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ userName, password, mail }),
  })
    .then((response) => response.json())
    .then((data) => {
      // 根据服务器返回的状态值判断登录是否成功
      if (data.state === 1) {
        //注册成功返回登录页面
        window.location.href = "login.html";
      } else {
        // 注册失败，创建一个新的机器人消息元素并设置内容为错误提示
        const botMessage = document.createElement("div");
        botMessage.className = "message bot";
        botMessage.textContent = data.message;
        chat.appendChild(botMessage);
      }
    })
    .catch((error) => {
      console.error("Error:", error);
    });
  // 您可以参考 loginUser 函数中的实现来完成此部分。
  console.log("注册信息：", {
    userName: userName,
    password: password,
    email: email,
  });
}
function checkuserNameAvailability(userName) {
  // 设置查询用户名是否存在的服务器端接口地址
  const url = "http://localhost:8080/getUser";

  // 向服务器发送用户名查询请求
  fetch(`${url}?userName=${userName}`, {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  })
    .then((response) => response.json())
    .then((data) => {
      // 根据服务器返回的状态值判断用户名是否可用
      if (data.state === 1) {
        // 用户名可用，继续注册流程
        askForPassword();
      } else {
        // 用户名已存在，提示用户并返回到输入用户名的步骤
        showuserNameExists();
      }
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}

function showuserNameExists() {
  const botMessage = document.createElement("div");
  botMessage.className = "message bot";
  botMessage.textContent = "用户名已存在，请尝试其他用户名：";
  chat.appendChild(botMessage);
  step = "userName";
}
