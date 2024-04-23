import Header from "@/components/Header";
import Sidebar from "@/components/Sidebar";

const layout = ({ children }: { children: React.ReactNode }) => {
    return (
        <Sidebar>
            <div className="w-full h-full">
                <Header>{children}</Header>
            </div>
        </Sidebar>
    );
};

export default layout;