using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using System;
using TestStack.White.UIItems.ListBoxItems;

namespace AutomationTests.Tests.Sprint
{
    [TestFixture]
    class ManageSprintAsNonScrumMaster : BaseTestClass
    {
        private CreateSprintPage _createSprintPage;
        private ManageSprintsPage _manageSprintsPage;
        private ProjectDashboardPage _projectDashboardPage;
        private UserDashboardPage _userDashboardPage;

        [OneTimeSetUp]
        public void OneTimeSetupCreateSprint()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);
            _manageSprintsPage = new ManageSprintsPage(MainWindow);
            _createSprintPage = new CreateSprintPage(MainWindow);

            LoginPage.Login("user3@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            _userDashboardPage.GetProjectListItem("e2eProjectName1").Click();
            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());

            _projectDashboardPage.ManageSprintsButton.Click();

            var messageBox = MessageBoxUtil.GetInfoMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            MessageBoxUtil.ClickOKButton(messageBox);

            Assert.IsTrue(_manageSprintsPage.IsCurrentPage());
        }

        [Test]
        public void ShouldSuccessfullyNotAllowViewCreateSprintAsNonScrumMaster()
        {
            Assert.IsTrue(_manageSprintsPage.IsCurrentPage());
            Assert.False(_manageSprintsPage.CreateSprintButton.Visible);
        }

        private void EnterValidCredentials()
        {
            _createSprintPage.SprintNameTextBox.Text = "e2eSprintName";
            _createSprintPage.StartDatePicker.Date = DateTime.Now.AddDays(20);
            _createSprintPage.EndDatePicker.Date = DateTime.Now.AddDays(30);
        }
    }
}
