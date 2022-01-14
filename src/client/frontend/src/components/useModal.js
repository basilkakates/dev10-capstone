import { useState } from "react"

const useModal = () => {
    const [isVisible, setIsVisible] = useState(false);

    function toggleModal() {
        setIsVisible(!isVisible);
    }

    function viewModal() {
        setIsVisible(true);
    }

    return {
        isVisible,
        toggleModal,
        viewModal
    }
};

export default useModal;

