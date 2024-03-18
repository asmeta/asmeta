import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap';

import api from './api'

addEventListener("DOMContentLoaded", async (event) => {
    let runningModels = await api.getRunningModels()
    if (runningModels) {
        for (const id in runningModels.models) {
            let card = createModelCard(id, runningModels.models[id])

            card.addEventListener('click', () => goToDetail(id))

            document.getElementById("card-container").appendChild(card)
        }
    }
});

function createModelCard(modelId, modelName) {
    let col = document.createElement("div")
    col.classList.add("col")
    
    let card = document.createElement("div")
    card.classList.add("card", "shadow-sm")

    let colorIndex = (modelId - 1) % 9;

    let svg = '<svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" preserveAspectRatio="xMidYMid slice" focusable="false">' +
                    '<title>' + modelName + '"</title>' +
                    '<rect width="100%" height="100%" fill="' + backgroundColors[colorIndex] + ' "/>' +
                    '<text x="50%" y="50%" fill="#eceeef" dy=".3em">' + modelName + '</text>' +
                '</svg>' +
                '<div class="card shadow-sm">' +
                    '<div class="card-body">' +
                    '<p class="card-text">Id: ' + modelId+ ' </p>'+
                        '<div class="d-flex justify-content-between align-items-center">' +
                            '<small class="text-muted">9 mins</small>' +
                        '</div>' +
                    '</div>' + 
                '</div>'
    
    col.innerHTML = svg

    return col
}

function goToDetail(id) {
    window.location.href = "/detail.html?id=" + id
}

const backgroundColors = [
    "#3F51B5", // Blu
    "#2196F3", // Blu chiaro
    "#F44336", // Rosso
    "#E91E63", // Rosa
    "#9C27B0", // Viola
    "#673AB7", // Indaco
    "#03A9F4", // Azzurro
    "#00BCD4", // Azzurro chiaro
    "#009688", // Verde
    "#4CAF50"  // Verde chiaro
  ];