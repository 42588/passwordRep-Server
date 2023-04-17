document.addEventListener("DOMContentLoaded", function () {
  fillCurrentUrl();
  addFormSubmitListener();
  document
    .getElementById("goto-options")
    .addEventListener("click", function () {
      window.location.href = "view_password.html";
    });
});

function fillCurrentUrl() {
  chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
    var currentTab = tabs[0];
    document.getElementById("url-input").value = currentTab.url;
  });
}

function addFormSubmitListener() {
  document
    .getElementById("myForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      submitData();
    });
}

function submitData() {
  var username = document.getElementById("username-input").value;
  var password = document.getElementById("password-input").value;
  var url = document.getElementById("url-input").value;

  var data = {
    userName: username,
    password: password,
    website: url,
  };

  var xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8080/save", true);
  xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      document.getElementById("message").innerHTML = "数据提交成功！";
    } else if (xhr.readyState === 4) {
      document.getElementById("message").innerHTML = "数据提交失败！";
    }
  };
  xhr.send(JSON.stringify(data));
}
