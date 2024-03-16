import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap';
import { Modal } from 'bootstrap';

import VariableComponent from './variableComponent.js';

import api from './api.js'
let id = 0;
let variableComponent = new VariableComponent();
let modalError;
let inputType = 1;

addEventListener("DOMContentLoaded", async (event) => {
    const urlParams = new URLSearchParams(window.location.search);
    id = urlParams.get('id');

    modalError = new Modal(document.getElementById('modal-error'), {});
    let currentStatus = await api.getCurrentStatus(id)

    if (currentStatus) {
        document.getElementById("model-name").innerHTML = currentStatus.modelName

        updateUi(currentStatus)
    } else {
        window.location.href = "/index.html"   
    }
});

async function updateUi(currentStatus) {
    let color = "green";
    if (currentStatus.runOutput.esit !== "SAFE") {
        color = "red"
    }

    document.getElementById("status").innerHTML =  "State: <b style='color: " + color +"'>" + currentStatus.runOutput.esit + "</b>"

    const monitoredTable = document.getElementById("monitored-table").getElementsByTagName("tbody")[0];
    monitoredTable.innerHTML = ''
    for (const key in currentStatus.runOutput.controlledvalues) {
        creteVariableTableRow(monitoredTable, key, currentStatus.runOutput.controlledvalues[key])
    }

    const outputTable = document.getElementById("output-table").getElementsByTagName("tbody")[0];
    outputTable.innerHTML = ''
    for (const key in currentStatus.runOutput.runOutput) {
        creteVariableTableRow(outputTable, key, currentStatus.runOutput.outvalues[key])
    }

    const monitoredList = document.getElementById("monitored-list")
    monitoredList.innerHTML = ""
    currentStatus.monitored.forEach(monitored => {
        const li = document.createElement("li");
        li.innerHTML = monitored

        monitoredList.appendChild(li)
    })

    document.getElementById("add-variable").addEventListener("click", addVariableEvent)
    document.getElementById("make-step").addEventListener("click", makeStep)
    document.getElementById("stop-model").addEventListener("click", stopModel)
    document.getElementById("select-input-type").addEventListener("change", changeInputType)
}


const addVariableEvent = () => {    
    var row = variableComponent.createNewVariabile()
    
    document.getElementById("insert-variables-container").appendChild(row)
}

async function makeStep() {
    let resp;
    if (inputType == 1) {
        let variable = variableComponent.getVariables()
        resp = await api.step(id, variable)
    } else {
        let variable = document.getElementById("variables-string").value
        resp = await api.stepSingleInput(id, variable)
    }
    
    if (resp) {
        if (resp.runOutput.esit == "UNSAFE") {
            showModal(resp.runOutput.message)
        } else {
            updateUi(resp)
            showSuccessAlert()
        }
    }
}

async function stopModel() {
    const resp = await api.stopModel(id)
    if (resp) {
        window.location.href = "/index.html"   
    }
}

function creteVariableTableRow(table, variableName, variableValue) {
    var row = table.insertRow();

    var nameCell = row.insertCell(0);
    var valueCell = row.insertCell(1);

    nameCell.innerHTML = variableName;
    valueCell.innerHTML = variableValue;

    return row
}

function changeInputType(event) {
    document.getElementById("insert-variables-container").classList.toggle("d-none")
    document.getElementById("add-variable").classList.toggle("d-none")

    document.getElementById("insert-string-variable-container").classList.toggle("d-none")
    console.log(event.target.value)
    inputType = event.target.value;
}

function showModal(errorText) {
    let modalTextError = document.getElementById("error-modal-text")
    modalTextError.innerHTML = "The current step generated an exception:<br>"
    var strongElement = document.createElement("strong");
    strongElement.appendChild(document.createTextNode(" " + errorText + " "));
    modalTextError.appendChild(strongElement)
    
    modalError.show();
}

function showSuccessAlert() {
    var successAlert = document.getElementById("alert-success")
    successAlert.classList.add("show")
    successAlert.classList.remove("fade")

    setTimeout(() => {
        successAlert.classList.add("fade")
        successAlert.classList.remove("show")
      }, 1500);
}