//const slides = [
//  { content: "Main Slide 1<br><a onclick='changeSubSlides(1)'>Change subs</a>", subs: ["A1", "A2", "A3"] },
//  { content: "Main Slide 2<br><a onclick='changeSubSlides(2)'>Change subs</a>", subs: ["B1", "B2"] },
//  { content: "Main Slide 3<br><a onclick='changeSubSlides(3)'>Change subs</a>", subs: ["C1", "C2", "C3", "C4"] }
//];
//
//let current = 0;
//
//function render() {
//  document.getElementById("main-content").innerHTML = slides[current].content;
//  renderSubSlides(slides[current].subs);
//}
//
//function renderSubSlides(subs) {
//  const container = document.getElementById("sub-slides-container");
//  container.innerHTML = "";
//  subs.forEach(s => {
//    const div = document.createElement("div");
//    div.className = "sub-slide";
//    div.textContent = s;
//    container.appendChild(div);
//  });
//}
//
//function prevSlide() {
//  current = (current - 1 + slides.length) % slides.length;
//  render();
//}
//
//function nextSlide() {
//  current = (current + 1) % slides.length;
//  render();
//}
//
//function changeSubSlides(set) {
//  if (set === 1) renderSubSlides(["X1", "X2", "X3"]);
//  if (set === 2) renderSubSlides(["Y1", "Y2"]);
//  if (set === 3) renderSubSlides(["Z1", "Z2", "Z3", "Z4"]);
//}
//
//render();
