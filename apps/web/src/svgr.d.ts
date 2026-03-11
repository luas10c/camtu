declare module '*.svg' {
  export const ReactComponent: React.FunctionComponent<
    React.SVGProps<SVGElement>
  > = () => null
}

declare module '*.svg?url' {
  const content: string
  export default content
}
