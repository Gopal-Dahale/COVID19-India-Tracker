function createNode(element) {
  return document.createElement(element);
}

function append(parent, el) {
  return parent.appendChild(el);
}

var statesData = [];
const url = "https://api.covid19india.org/data.json";
fetch(url)
  .then((resp) => resp.json())
  .then(function (data) {
    let states = data.statewise;
    return states.map(function (state) {
      statesData.push({
        stateName: state.state,
        confirmed: state.confirmed,
        active: state.active,
        recovered: state.recovered,
        deaths: state.deaths,
        deltaconfirmed: state.deltaconfirmed,
        deltarecovered: state.deltarecovered,
        deltadeaths: state.deltadeaths,
      });
      // colouring each state
      var capturedState = document.getElementById("IN-" + state.statecode);
      if (capturedState != null) {
        var value = parseInt(state.confirmed);

        switch (true) {
          case value <= 1000:
            capturedState.classList.add("color0");
            break;
          case value <= 5000:
            capturedState.classList.add("color1");
            break;
          case value <= 15000:
            capturedState.classList.add("color2");
            break;
          case value <= 30000:
            capturedState.classList.add("color3");
            break;
          case value <= 50000:
            capturedState.classList.add("color4");
            break;
          default:
            capturedState.classList.add("color5");
            break;
        }
      } 
    });
  })
  .catch(function (error) {
    console.log(error);
  });

// function colourCountry(name, colour) {
//   var country = document.getElementById(name);
//   country.className += " colour" + colour;
// }
// function colourCountries(data) {
//   for (var colour = 0; colour < data.length; colour++) {
//     for (var country = 0; country < data[colour].length; country++) {
//       colourCountry(data[colour][country], colour + 1);
//     }
//   }
// }

function displayData(title) {
  var capturedState = document.getElementById("confirmed-data");
  statesData.find(function (state) {
    if (state.stateName == title) {
      capturedState.innerHTML = `${state.confirmed}`;

      document.getElementById("active-data").innerHTML = `${state.active}`;

      document.getElementById(
        "recovered-data"
      ).innerHTML = `${state.recovered}`;

      document.getElementById("deceased-data").innerHTML = `${state.deaths}`;

      document.getElementById(
        "deltaconfirmed-data"
      ).innerHTML = `+${state.deltaconfirmed}`;

      document.getElementById(
        "deltarecovered-data"
      ).innerHTML = `+${state.deltarecovered}`;

      document.getElementById(
        "deltadeceased-data"
      ).innerHTML = `+${state.deltadeaths}`;

      document.getElementById("stateName").innerHTML = `${state.stateName}`;
    }
  });
}

function totalCounts(){
  document.getElementById("stateName").innerHTML = `${statesData[0].stateName}`
  document.getElementById("active-data").innerHTML = `${statesData[0].active}`
  document.getElementById("confirmed-data").innerHTML= `${statesData[0].confirmed}`
  document.getElementById("recovered-data").innerHTML = `${statesData[0].recovered}`
  document.getElementById("deceased-data").innerHTML = `${statesData[0].deaths}`

  document.getElementById("deltaconfirmed-data").innerHTML= `+${statesData[0].deltaconfirmed}`
  document.getElementById("deltadeceased-data").innerHTML= `+${statesData[0].deltadeaths}`
  document.getElementById("deltarecovered-data").innerHTML= `+${statesData[0].deltarecovered}`
}