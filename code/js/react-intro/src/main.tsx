import { createRoot } from "react-dom/client";
//import {main} from './example-first/main.js'
import {App} from './example-state/App.tsx'
//import { App } from "./example-timer/App.tsx";
// import { App } from "./example-lap-timer/App.tsx";
// import { App } from "./example-context/App.tsx";

//console.log("main starting");
//main()

const container = document.getElementById("container")!;
const root = createRoot(container);
root.render(<App />);
