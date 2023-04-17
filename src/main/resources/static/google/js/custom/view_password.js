document.addEventListener("DOMContentLoaded", function () {
  getUserData();
  document
    .getElementById("backToSubmit")
    .addEventListener("click", function () {
      window.location.href = "submit_password.html";
    });
});

function getUserData() {
  var userDataUrl = "http://localhost:8080/userdata";

  var xhr = new XMLHttpRequest();
  xhr.open("GET", userDataUrl, true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      var userData = JSON.parse(xhr.responseText);
      displayUserData(userData);
    }
  };
  xhr.send();
}
///////
function createTableHeader() {
  const thead = document.createElement("thead");
  thead.innerHTML = `
    <tr>
      <th>用户名</th>
      <th>密码</th>
      <th>网址</th>
      <th>操作</th>
    </tr>
  `;
  return thead;
}

function createTableRow(item) {
  const tr = document.createElement("tr");

  tr.innerHTML = `
    <td>${item.userName}</td>
    <td>${item.password}</td>
    <td>${item.website}</td>
    <td>
      <button class="edit">编辑</button>
      <button class="delete">删除</button>
      <button class="copy">复制</button>
    </td>
  `;

  attachEventListeners(tr, item);
  return tr;
}

function displayUserData(userData) {
  const container = document.getElementById("user-data");
  const table = document.createElement("table");
  table.appendChild(createTableHeader());

  const tbody = document.createElement("tbody");
  userData.forEach((item) => {
    const tr = createTableRow(item);
    tbody.appendChild(tr);
  });
  table.appendChild(tbody);

  container.innerHTML = "";
  container.appendChild(table);
}

function attachEventListeners(element, item) {
  element
    .querySelector(".edit")
    .addEventListener("click", () => editItem(item));
  element
    .querySelector(".delete")
    .addEventListener("click", () => deleteItem(item));
  element
    .querySelector(".copy")
    .addEventListener("click", () => copyItem(item));
}

function editItem(item) {
  // Edit item functionality
}

function deleteItem(item) {
  // Delete item functionality
}

function copyItem(item) {
  const dummy = document.createElement("textarea");
  document.body.appendChild(dummy);
  dummy.value = `用户名: ${item.userName}\n密码: ${item.password}\n网址: ${item.website}`;
  dummy.select();
  document.execCommand("copy");
  document.body.removeChild(dummy);

  alert("数据已复制到剪贴板");
}
