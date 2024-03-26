import { ReactElement, JSXElementConstructor, ReactNode, ReactPortal, AwaitedReactNode } from "react";

export default function Layout(props:
{ children:
        string |
        number |
        boolean |
        ReactElement<any, string | JSXElementConstructor<any>> |
        Iterable<ReactNode> |
        ReactPortal |
        Promise<AwaitedReactNode> |
        null |
        undefined; }){
    return (
        <html>
            <body>{props.children}</body>
        </html>
    );
}