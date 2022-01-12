function RunTime({timestamp}) {
  let date = new Date(timestamp);

  return (
    <>
      <td>
        {
          date.toLocaleDateString()
        }
      </td>
      <td>
        {
          date.toLocaleTimeString()
        }
      </td>
    </>
  );


}

export default RunTime;

