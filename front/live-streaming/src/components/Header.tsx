import PagePadding from "@/components/PagePadding";
import UserIcon from "@/components/UserIcon";
import {FaChromecast} from "react-icons/fa";
import {FiSearch} from "react-icons/fi";
import {auth} from "@/auth";
import Link from "next/link";


const Header = async ({ children }: { children: React.ReactNode }) => {
    const session = await auth();

    return (
        <header className="relative overflow-y-auto w-full h-full">
            <section className=" absolute top-0 w-full">
            </section>
            {/* searchSection */}
            <section className="sticky">
                <PagePadding>
                    <div className="h-[64px] flex flex-row justify-between items-center">
                        <article className="h-[42px] min-w-[480px] flex flex-row items-center bg-[rgba(0,0,0,0.14)] rounded-2xl px-[16px] gap-[16px]">
                            <div>
                                <FiSearch size={24}/>
                            </div>
                            <input className="h-full w-full bg-transparent " placeholder="검색" type="text"/>
                        </article>
                        <article className="flex flex-row gap-6 items-center">
                            { session ? <>
                                        <FaChromecast size={26}/>
                                        <UserIcon/>
                                    </> :
                                <Link href="/login">로그인</Link>
                            }
                        </article>
                    </div>
                </PagePadding>
            </section>
            <section className="relative">{children}</section>
        </header>
    );
};

export default Header;