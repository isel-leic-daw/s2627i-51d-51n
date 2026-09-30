import { useState } from "react";

function dateStringFrom(date: Date) {
  return `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`
}

export function Counter2({ label }: { label: string }) {
  const [observedCount, setCount] = useState<number>(0);
  const [observedTimestamp, setTimestamp] = useState<Date | null>(null);
  console.log("render", {
    label: label,
    observedCount: observedCount,
    observedTimestamp: observedTimestamp,
  });
  const lastClicked = observedTimestamp
    ? dateStringFrom(observedTimestamp)
    : "never";
  const handleClick = () => {
    setCount(observedCount + 1);
    setTimestamp(new Date());
  };
  return (
    <div>
      <h2>{label}</h2>
      <p>Counter: {observedCount}</p>
      <p>Last clicked on: {lastClicked}</p>
      <button onClick={handleClick}>Up</button>
    </div>
  );
}
