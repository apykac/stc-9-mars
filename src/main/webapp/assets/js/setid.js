$(".m_editSubject").click(function () {
    document.getElementById("win_subj").removeAttribute("style");
    document.getElementById("m_id").value = $(this).attr("idsubj");
    document.getElementById("m_name").value = $(this).attr("namesubj");
});
$(".m_editLessons").click(function () {
    document.getElementById("win_less").removeAttribute("style");
    document.getElementById("l_id").value = $(this).attr("idless");
    document.getElementById("l_subjid").value = $(this).attr("subjidless");
    document.getElementById("l_sname").value = $(this).attr("snameless");
    document.getElementById("l_date").value = $(this).attr("dateless");
    document.getElementById("l_name").value = $(this).attr("nameless");
    document.getElementById("l_name2").value = $(this).attr("nameless");
});