
import axios from 'axios';

async function getRunningModels() {
    let resp = await axios.get("http://localhost:8080/running-models")

    if (resp.status == 200)
        return resp.data
    else 
        return false
}

async function getModelList() {
    let resp = await axios.get("http://localhost:8080/model-list")

    if (resp.status == 200)
        return resp.data

    return false
}

async function getCurrentStatus(id) {
    try {
        let resp = await axios.get("http://localhost:8080/get-model-status?id=" + id)
        if (resp.status == 200) 
            return resp.data
    } catch(ex) {
        return false
    }
    
    return false;
}   

async function startModel(modelName) {
    const resp = await axios.post("http://localhost:8080/start?name=" + modelName)
    if (resp.status == 200)
        return resp.data.id
    
    return false
}

async function step(id, monitoredVariables) {
    try {
        const resp = await axios.put("http://localhost:8080/step", {
            id: id, 
            monitoredVariables: monitoredVariables
        })
    
        if (resp.status == 200)
            return resp.data
    
    } catch {
        return false
    }

    return false
}

async function stepSingleInput(id, variableString) {
    const resp = await axios.put("http://localhost:8080/step-single-input", {
        id: id, 
        monitoredVariable: variableString
    })

    if (resp.status == 200)
        return resp.data

    return false
}

async function stopModel(id) {
    let resp = await axios.delete("http://localhost:8080/stop-model?id=" + id)
    if (resp.status == 200) 
        return resp.data
    
    return false;
}   

async function deleteModel(modelName) {
    let resp = await axios.delete("http://localhost:8080/delete-model?name=" + modelName)
    if (resp.status == 200) 
        return resp.data
    
    return false;
}  

async function deleteLibary(libraryName) {
    let resp = await axios.delete("http://localhost:8080/delete-library?name=" + libraryName)
    if (resp.status == 200) 
        return resp.data
    
    return false;
}  

export default {
    getRunningModels,
    getModelList, 
    getCurrentStatus,
    startModel,
    step,
    stopModel,
    deleteModel,
    deleteLibary,
    stepSingleInput
}