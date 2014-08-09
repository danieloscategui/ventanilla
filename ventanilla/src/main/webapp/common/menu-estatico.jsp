<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="cssHorizontalMenu.vm" permissions="rolesAdapter">
<ul id="primary-nav" class="menuList">
    <li class="pad">&nbsp;</li>
    <menu:displayMenu name="MainMenu"/>
    <menu:displayMenu name="AdminMenu"/>
    <menu:displayMenu name="ProgramaMenu"/>
    <menu:displayMenu name="PremioMenu"/>
    <menu:displayMenu name="FlotaMenu"/>
    <menu:displayMenu name="SecurityMenu"/>
    <menu:displayMenu name="Logout"/>
</ul>
</menu:useMenuDisplayer>