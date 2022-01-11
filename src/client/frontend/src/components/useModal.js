import { useState } from "react"

const useModal = () => {
    const [isVisible, setIsVisible] = useState(false);
    const [runId, setRunId] = useState();

    function toggleModal() {
        setIsVisible(!isVisible);
    }

    function viewModal() {
        setIsVisible(true);
    }

    return {
        isVisible,
        toggleModal,
        viewModal,
        runId,
        setRunId
    }
};

export default useModal;

