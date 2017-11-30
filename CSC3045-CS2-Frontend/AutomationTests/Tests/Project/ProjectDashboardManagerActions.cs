using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WPFUIItems;

namespace AutomationTests.Tests.Project
{
    [TestFixture]
    public class ProjectDashboardManagerActions : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;
        private ProjectDashboardPage _projectDashboardPage;

        [OneTimeSetUp]
        public void OneTimeSetupProjectDashboardManagerActions()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);
            
            LoginPage.Login("user2@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            _userDashboardPage.GetProjectListItem("e2eProjectName1").Click();

            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());
            Assert.AreEqual("e2eForename2 e2eSurname2", _projectDashboardPage.ProjectManagerNameTextBlock.Text);
        }

        [Test]
        public void ShouldSuccessfullySearchForAndAddExistingUserToTeam()
        {
            _projectDashboardPage.SearchEmailTextBox.Text = "user10@e2e.com";
            _projectDashboardPage.SearchButton.Click();

            Assert.AreEqual("e2eForename10 e2eSurname10", _projectDashboardPage.SearchResultName.Text);
            _projectDashboardPage.AddToTeamButton.Click();

            // TODO:: Figure out why the list is only coming back with 4 items
            //Assert.NotNull(_projectDashboardPage.GetTeamMemberListItem("e2eForename10 e2eSurname10"));
        }

        [Test]
        public void ShouldFailOnSearchingNonExistingUser()
        {
            _projectDashboardPage.SearchEmailTextBox.Text = "notauser@e2e.com";
            _projectDashboardPage.SearchButton.Click();

            var messageBox = MessageBoxUtil.GetErrorMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("not exist"));

            MessageBoxUtil.ClickOKButton(messageBox);
        }

        [Test]
        public void ShouldSetUserInTeamAsProductOwner()
        {
            WPFListItem teamMemberListItem = _projectDashboardPage.GetTeamMemberListItem("e2eForename4 e2eSurname4");
            Assert.NotNull(teamMemberListItem);

            _projectDashboardPage.GetSetProductOwnerButtonForListItem(teamMemberListItem).Click();

            Assert.AreEqual("e2eForename4 e2eSurname4", _projectDashboardPage.ProductOwnerNameTextBlock.Text);
        }

        [Test]
        public void ShouldSetUserInTeamAsScrumMaster()
        {
            WPFListItem teamMemberListItem = _projectDashboardPage.GetTeamMemberListItem("e2eForename6 e2eSurname6");
            Assert.NotNull(teamMemberListItem);

            _projectDashboardPage.GetSetScrumMasterButtonForListItem(teamMemberListItem).Click();

            Assert.NotNull(_projectDashboardPage.GetScrumMasterListItem("e2eForename6 e2eSurname6"));
        }

        [Test]
        public void ShouldRemoveUserFromScrumMasters()
        {
            WPFListItem scrumMasterListItem = _projectDashboardPage.GetScrumMasterListItem("e2eForename5 e2eSurname5");
            Assert.NotNull(scrumMasterListItem);

            _projectDashboardPage.GetRemoveButtonForScrumMasterListItem(scrumMasterListItem).Click();

            Assert.Null(_projectDashboardPage.GetScrumMasterListItem("e2eForename5 e2eSurname5"));
        }
    }
}
