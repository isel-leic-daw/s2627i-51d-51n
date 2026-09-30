import { useState } from "react";

const HEADING_LEVELS = ["h1", "h2", "h3", "h4"] as const;
type HeadingLevel = (typeof HEADING_LEVELS)[number];
const HEADING_LEVELS_SET = new Set<string>(HEADING_LEVELS);

export function asHeading(value: string): HeadingLevel {
  if (HEADING_LEVELS_SET.has(value)) {
    // oxlint-disable-next-line typescript/no-unsafe-type-assertion checked by the previous
    return value as HeadingLevel;
  }
  return "h1";
}

export type CounterProps = {
  label: string;
  headingLevel: HeadingLevel;
};
export function Counter({ label, headingLevel }: CounterProps) {
  const [observedCount, setCount] = useState<number>(0);
  console.log("render", { label: label, observedCount: observedCount });
  const HeadingElemName = headingLevel;
  return (
    <div>
      <HeadingElemName>{label}</HeadingElemName>
      <p>Counter: {observedCount}</p>
      <button onClick={() => setCount(observedCount + 1)}>Up</button>
    </div>
  );
}
