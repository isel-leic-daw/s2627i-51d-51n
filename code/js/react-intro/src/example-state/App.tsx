import { useState } from "react";
import { Counter, CounterProps, asHeading } from "./Counter";
// import { Counter2 } from "./Counter2";

export function App() {
  const [heading, setHeading] = useState<CounterProps['headingLevel']>('h2')
  const checkedHeading = heading 
  return (
    <div>
      <h1>State</h1>
      <p>Select heading to use on the counters:</p>
      <select value={heading} onChange={(ev) => setHeading(asHeading(ev.target.value))}>
        <option value="h1">h1</option>
        <option value="h2">h2</option>
        <option value="h3">h3</option>
        <option value="h4">h4</option>
      </select>
      <Counter label="First counter" headingLevel={checkedHeading}/>
      <Counter label="Second counter" headingLevel={checkedHeading}/>
    </div>
  );
}
