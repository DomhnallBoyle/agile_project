using AutomationTests.PageTemplates;
using NUnit.Framework;
using TestStack.White.Factory;
using TestStack.White.UIItems.MenuItems;

namespace AutomationTests.Tests.Project
{
    [TestFixture]
    public class ProjectDashboardNonManagerActions : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;
        private ProjectDashboardPage _projectDashboardPage;

        [OneTimeSetUp]
        public void OneTimeSetupProjectDashboardNonManagerActions()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);

            LoginPage.Login("user3@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            _userDashboardPage.GetProjectListItem("e2eProjectName1").Click();

            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());
        }

        [Test]
        public void UserSearchBarShouldNotBeVisible()
        {
            Assert.False(_projectDashboardPage.SearchEmailTextBox.Visible);
            Assert.False(_projectDashboardPage.SearchButton.Visible);
        }

        [Test]
        public void ProjectTeamListShouldNotAllowContextMenu()
        {
            _projectDashboardPage.TeamMembersListBox.RightClick();

            Menus popItems;

            Assert.Throws<UIItemSearchException>(() => popItems = MainWindow.Popup.Items);
        }
    }
}
