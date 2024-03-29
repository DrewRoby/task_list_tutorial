"use strict"

const csrfToken = document.getElementById("csrfToken").value;
const validateRoute = document.getElementById("validateRoute").value;
const tasksRoute = document.getElementById("tasksRoute").value;
const createRoute = document.getElementById("createRoute").value;
const deleteRoute = document.getElementById("deleteRoute").value;
const addRoute = document.getElementById("addRoute").value;
const logoutRoute = document.getElementById("logoutRoute").value;

function setCaretPosition(ctrl, pos) {
  // Modern browsers
  if (ctrl.setSelectionRange) {
    ctrl.focus();
    ctrl.setSelectionRange(pos, pos);

  // IE8 and below
  } else if (ctrl.createTextRange) {
    var range = ctrl.createTextRange();
    range.collapse(true);
    range.moveEnd('character', pos);
    range.moveStart('character', pos);
    range.select();
  }
}


function login() {
  const username = document.getElementById("loginName").value;
  const password = document.getElementById("loginPass").value;
  fetch(validateRoute, {
    method: 'POST',
    headers: {'Content-Type':'application/json', 'Csrf-Token': csrfToken},
    body: JSON.stringify({ username, password })
    }).then(res => res.json()).then(data => {
      if(data) {
        document.getElementById("login-section").hidden = true;
        document.getElementById("task-section").hidden = false;
        document.getElementById("create-message").innerHTML = "";
        document.getElementById("login-message").innerHTML = "";
        loadTasks();
      } else {
        document.getElementById("login-message").innerHTML = "Login Failed";
      }
    });
  }

function createUser() {
  const username = document.getElementById("createName").value;
  const password = document.getElementById("createPass").value;
  fetch(createRoute, {
    method: 'POST',
    headers: {'Content-Type':'application/json', 'Csrf-Token': csrfToken},
    body: JSON.stringify({ username, password })
    }).then(res => res.json()).then(data => {
      if(data) {
        document.getElementById("login-section").hidden = true;
        document.getElementById("task-section").hidden = false;
        document.getElementById("login-message").innerHTML = "";
        document.getElementById("create-message").innerHTML = "";
        loadTasks();
      } else {
        document.getElementById("login-message").innerHTML = "Create User Failed";
      }
    });
  }

function loadTasks() {
  const ul = document.getElementById("task-list");
  ul.innerHTML = "";
  fetch(tasksRoute).then(res => res.json()).then(tasks => {
    for (let i = 0; i < tasks.length; ++i) {
      const li = document.createElement("li");
      const text = document.createTextNode(tasks[i]);
      li.appendChild(text);
      li.onclick = e => {
        fetch(deleteRoute, {
            method: 'POST',
            headers: {'Content-Type':'application/json', 'Csrf-Token': csrfToken},
            body: JSON.stringify(i)
          }).then(res => res.json()).then(data => {
            if(data) {
              document.getElementById("login-message").innerHTML = "";
              loadTasks();
            } else {
              document.getElementById("login-message").innerHTML = "Delete Task Failed";
          }
      });
      }
      ul.appendChild(li);
    }
  });
}

function addTask() {
  const task = document.getElementById("newTask").value;
  fetch(addRoute, {
      method: 'POST',
      headers: {'Content-Type':'application/json', 'Csrf-Token': csrfToken},
      body: JSON.stringify(task)
      }).then(res => res.json()).then(data => {
        if(data) {
          loadTasks();
          document.getElementById("newTask").value = "";
          const input = document.getElementById("newTask")
          setCaretPosition(input, input.value.length);
          document.getElementById("task-message").innerHTML = "";
        } else {
        document.getElementById("task-message").innerHTML = "Add Task Failed";
        }
      });
  }

function logout() {
  fetch(logoutRoute).then(res => res.json()).then(tasks => {
        document.getElementById("login-section").hidden = false;
        document.getElementById("task-section").hidden = true;

  });
}
