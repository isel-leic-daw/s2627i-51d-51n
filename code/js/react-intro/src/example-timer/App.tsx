import { useState } from "react";
import { Timer } from "./Timer.tsx";
export function App() {
  const [observedShowSecondTimer, setShowSecondTimer] =
    useState<boolean>(false);
  const [observedPeriod, setPeriod] = useState<number>(2000);
  const buttonText = observedShowSecondTimer
    ? "disable second timer"
    : "enable second timer";
  return (
    <div>
      <div>
        <select
          value={observedPeriod}
          onChange={(ev) => setPeriod(Number(ev.target.value))}
        >
            <option value="100">100 ms</option>
            <option value="1000">1000 ms</option>
            <option value="2000">2000 ms</option>
            <option value="4000">4000 ms</option>
            <option value="8000">8000 ms</option>
        </select>
        <button onClick={() => setShowSecondTimer(!observedShowSecondTimer)}>
          {buttonText}
        </button>
      </div>
      <Timer label="First timer." period={observedPeriod}/>
      {observedShowSecondTimer && <Timer label="Second timer." period={observedPeriod}/>}
    </div>
  );
}
