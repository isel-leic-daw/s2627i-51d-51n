import { useEffect, useState } from "react";

type TimerProps = {
  label: string;
  period: number;
};

export function Timer(props: TimerProps) {
  const [observedCount, setCount] = useState(0);
  console.log("Render:", { ...props, observedCount });
  useEffect(() => {
    // oxlint-disable-next-line react/exhaustive-effect-dependencies learning purposes
    console.log("Effect:", { ...props, observedCount });
    const intervalId = setInterval(() => {
      console.log("setInterval callback:", { ...props, observedCount });
      setCount((currState) => {
        console.log("Update:", { ...props, observedCount, currState });
        return currState + 1;
      });
    }, props.period);
    return () => {
      console.log("Effect cleanup:", { ...props, observedCount });
      clearInterval(intervalId);
    };
    // oxlint-disable-next-line react-hooks/exhaustive-deps learning purposes 
  }, [props.period]);
  return (
    <div>
      <h2>{props.label}</h2>
      <p>{observedCount}</p>
    </div>
  );
}
