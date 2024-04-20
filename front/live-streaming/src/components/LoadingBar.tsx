"use client"

import {BarLoader} from "react-spinners";

const loading = () => {
    return (
        <div className="w-full">
            <BarLoader color={"#000"} cssOverride={{width: "100%"}}/>
        </div>
    );
}