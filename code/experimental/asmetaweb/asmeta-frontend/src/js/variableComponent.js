export default class VariableComponent {

    constructor(container) {
        this.numberOfVariable = 0;
        this.container = container
    }

    getVariables() {
        let variable = {}
        for (let i = 0; i <= this.numberOfVariable; i++) {
            console.log("name-" + i)
            let name = document.getElementById("name-" + i).value
            let value = document.getElementById("value-" + i).value
            if (name != "" && value != "") {
                variable[name] = value
            }
        }

        return variable
    }

    createNewVariabile() {
        this.numberOfVariable+= 1;

        var row = document.createElement('div');
        row.classList.add("row", "variable-row")
        
        var firstCol = document.createElement("div")
        firstCol.classList.add("col")

        var nameInput = document.createElement("input")
        nameInput.classList.add("form-control")
        nameInput.type = "text";
        nameInput.placeholder = "Variable Name";
        nameInput.id = "name-" + this.numberOfVariable;

        firstCol.appendChild(nameInput)

        var secondCol = document.createElement("div")
        secondCol.classList.add("col")

        var valueInput = document.createElement("input")
        valueInput.classList.add("form-control")
        valueInput.type = "text";
        valueInput.placeholder = "Value";
        valueInput.id = "value-" + this.numberOfVariable;

        secondCol.appendChild(valueInput)

        row.appendChild(firstCol)
        row.appendChild(secondCol)

        return row
    }
}