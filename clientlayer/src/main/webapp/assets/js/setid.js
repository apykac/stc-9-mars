$(".m_editSubject").click(function () {
    document.getElementById("win_subj").removeAttribute("style");
    document.getElementById("m_id").value = $(this).attr("idsubj");
    document.getElementById("m_name").value = $(this).attr("namesubj");
});
$(".m_editLessons").click(function () {
    document.getElementById("win_less").removeAttribute("style");
    document.getElementById("l_id").value = $(this).attr("idless");
    document.getElementById("l_name").value = $(this).attr("nameless");
});