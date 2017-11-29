using AutomationTests.PageTemplates;
using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WPFUIItems;

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
            Assert.AreEqual("e2eForename3 e2eSurname3", _projectDashboardPage.ProjectManagerNameTextBlock.Text);
        }

        [Test]
        public void UserSearchBarShouldNotBeVisible()
        {
            Assert.False(_projectDashboardPage.SearchEmailTextBox.Visible);
            Assert.False(_projectDashboardPage.SearchButton.Visible);
        }

        [Test]
        public void ProjectTeamListShouldNotDisplaySetAsButtons()
        {
            foreach (WPFListItem item in _projectDashboardPage.TeamMembersListBox.Items)
            {
                Assert.False(item.Get<Button>("SetProductOwnerButton").Visible);
                Assert.False(item.Get<Button>("SetScrumMasterButton").Visible);
            }
        }

        [Test]
        public void ScrumMasterListShouldNotDisplayRemoveButtons()
        {
            foreach (WPFListItem item in _projectDashboardPage.ScrumMastersListBox.Items)
            {
                Assert.False(item.Get<Button>("ScrumMasterRemoveButton").Visible);
            }
        }
    }
}
