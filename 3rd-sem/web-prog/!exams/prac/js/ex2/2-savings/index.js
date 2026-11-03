const form = document.querySelector("form");
const divContainer = document.querySelector(".container");

// a. (2 pts)
// Calculate last year's total energy consumption ( M )
// and output it to the console. For this, select all input fields,
// read the value of data-consumption attribute of each input as a number
// and calculate the sum.

const mul_reads = form.querySelectorAll("input");
let M = 0;
for (const single_read of mul_reads) {
  M += Number(single_read.dataset.consumption);
}
console.log(M);

function updateChart() {
  // b. (2 pts)
  // Calculate the current consumption ( ci ) for each input
  // and log the values to the console. Read the current value ( value ) of the input,
  // divide it by the maximum value ( max attribute of the input)
  // and multiply it by data-consumption. (ci=(value/max)*consumption).
  // Note: the value of ci matches the value of consumption when the page loads
  // since every slider is set to its maximum value.

  const ciValues = [...mul_reads].map(
    input => (Number(input.value) / Number(input.max)) * Number(input.dataset.consumption)
  );
  console.log(ciValues);

  // c. (2 pts)
  // Every input has a corresponding label element on chart under the form.
  // The label's 'for' attribute matches the 'id' attribute of the input it belongs to.
  // Set the 'label' element's width style property to ci / M * 100 percent (%)
  // for each input field (e.g. '65%' ).

  for (let i = 0; i < mul_reads.length; i++) {
	  const label = mul_reads[i].labels[0];
	  const percent = (ciValues[i] / M) * 100;
	  label.style.width = percent + "%";
	}
}

updateChart();

// d. (2 pts) Refresh the chart when the value of any input field is changed
// (event type: input). (Perform the previous three subtasks again.)
// Technical help: it is sufficient to listen for input events
// at the level of the form and recalculate the values.

form.addEventListener("input", updateChart);