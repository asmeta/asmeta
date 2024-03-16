import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap';
import 'dropzone/dist/basic.css';
import { Modal } from 'bootstrap';

import Dropzone from "dropzone";
import api from './api.js'

let id = 0;
let modelDropzone = undefined
let libraryDropzone = undefined
let modalError;

addEventListener("DOMContentLoaded", async (event) =>  {
    modalError = new Modal(document.getElementById('modal-error'), {});

    let resp = await api.getModelList()
    if (resp) {
        // Inizializza la lista di modelli
        let modelListContainer = document.getElementById("models-list")
        resp.models.forEach(modelName => {
            var entry = createListItem(modelName, false)
            modelListContainer.appendChild(entry);
        });

        // Inizializza la lista di librerie
        let librariesListContainer = document.getElementById("libraries-list")
        resp.libraries.forEach(libraryName => {
            var entry = createListItem(libraryName, true)
        
            librariesListContainer.appendChild(entry);
        });

        modelListContainer.addEventListener('click', selectModel)
        librariesListContainer.addEventListener('click', selectLibrary)
    }

    document.getElementById("upload-models-button").addEventListener("click", uploadModels)
    document.getElementById("upload-libraries-button").addEventListener("click", uploadLibraries)

    initFileDrop()
});

async function selectModel(event) {
    event.preventDefault()
    let modelName = event.target.dataset.value

    const resp = await api.startModel(modelName)
    if (resp && resp > 0) {
        id = resp
        window.location.href = "/detail.html?id=" + id    
    } else {
        modalError.show();
    }
}

async function selectLibrary(event) {
    console.log(event.target.dataset.value)
}

function initFileDrop() {
    Dropzone.autoDiscover = false;
    modelDropzone = new Dropzone("div#drop-area", { 
        url: "/upload-model",
        autoProcessQueue: false,
    });

    modelDropzone.on("addedfile", file => {
        console.log(`File added: ${file.name}`);
    });

    modelDropzone.on("queuecomplete", function (file) {
        // setTimeout(function() {
        //     location.reload()
        // }, 500);
    });

    libraryDropzone = new Dropzone("div#libraries-drop-area", { 
        url: "/upload-library",
        autoProcessQueue: false,
    });

    libraryDropzone.on("addedfile", file => {
        console.log(`File added: ${file.name}`);
    });

    libraryDropzone.on("queuecomplete", function (file) {
        setTimeout(function() {
            location.reload()
        }, 500);
    });
}

function uploadModels() {
    modelDropzone.processQueue();
}

function uploadLibraries() {
    libraryDropzone.processQueue();
}

async function deleteModel(event) {
    event.preventDefault();
    event.stopPropagation();
    
    const resp = await api.deleteModel(event.target.dataset.fileName)
    if (resp) 
        location.reload()
}

async function deleteLibrary(event) {
    event.preventDefault();
    event.stopPropagation();
    
    const resp = await api.deleteLibary(event.target.dataset.fileName)
    if (resp) 
        location.reload()
}

function createListItem(fileName, isLibrary) {
    var entry = document.createElement('li');
    entry.dataset.value = fileName

    var text = document.createElement('p')
    text.classList.add("flex-grow-1")
    text.dataset.value = fileName
    text.innerHTML = fileName
    entry.appendChild(text)

    entry.classList.add('list-group-item', "d-flex");
    entry.classList.add('list-group-item-action');
    
    var deleteButton = document.createElement("button")
    deleteButton.classList.add("btn", "btn-danger", "btn-delete")
    var deleteIcon = document.createElement("img")
    deleteIcon.src = "assets/icon_delete.svg"
    deleteIcon.height = 15
    deleteButton.appendChild(deleteIcon)
    
    deleteButton.dataset.fileName = fileName
    if (isLibrary)
        deleteButton.addEventListener('click', deleteLibrary);
    else 
        deleteButton.addEventListener('click', deleteModel);
    
    entry.appendChild(deleteButton)

    return entry
}