// 获取页面中的聊天区域和用户输入框元素
const chat = document.getElementById("chat");
const userInput = document.getElementById("userInput");

// 设置对话流程的初始状态
let step = "userName";
let userName, password;
const registerButton = document.getElementById("registerButton");
const clearChatButton = document.getElementById("clearChat");

clearChatButton.addEventListener("click", function () {
  const chat = document.getElementById("chat");
  chat.innerHTML = "";
});

registerButton.addEventListener("click", function () {
  window.location.href = "register.html";
});

// 为用户输入框添加键盘按下事件监听
userInput.addEventListener("keydown", function (event) {
  // 判断按下的键是否为“Enter”键
  if (event.key === "Enter") {
    // 阻止默认事件
    event.preventDefault();

    // 创建一个新的用户消息元素并设置内容为用户输入的文本
    const userMessage = document.createElement("div");
    userMessage.className = "message user";
    userMessage.textContent = userInput.value;

    // 根据当前步骤执行相应操作
    if (step === "userName") {
      // 保存用户输入的用户名
      userName = userInput.value;

      // 将用户消息添加到聊天区域并清空输入框
      chat.appendChild(userMessage);
      userInput.value = "";

      // 创建一个新的机器人消息元素并设置内容为询问密码
      const botMessage = document.createElement("div");
      botMessage.className = "message bot";
      botMessage.textContent = "输个密码呗~ 按回车继续";
      chat.appendChild(botMessage);

      // 设置下一步为“password”
      step = "password";
    } else if (step === "password") {
      // 保存用户输入的密码
      password = userInput.value;

      // 将用户消息添加到聊天区域并清空输入框
      chat.appendChild(userMessage);
      userInput.value = "";

      // 调用 loginUser 函数登录用户
      loginUser(userName, password);
    }
  }
});

// 登录用户函数
function loginUser(userName, password) {
  // 设置服务器端验证接口地址
  const url = "http://localhost:8080/login";

  // 发送用户名和密码到服务器端验证接口
  fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ userName, password }),
  })
    .then((response) => response.json())
    .then((data) => {
      // 根据服务器返回的状态值判断登录是否成功
      if (data.state === 1) {
        // 登录成功，跳转到 popup.html 页面
        window.location.href = "view_password.html";
      } else {
        // 登录失败，创建一个新的机器人消息元素并设置内容为错误提示
        const botMessage = document.createElement("div");
        botMessage.className = "message bot";
        botMessage.textContent = data.message + "\n" + "请再次尝试输入用户账号";
        chat.appendChild(botMessage);

        // 设置下一步为“userName”，重新开始对话流程
        step = "userName";
      }
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}
