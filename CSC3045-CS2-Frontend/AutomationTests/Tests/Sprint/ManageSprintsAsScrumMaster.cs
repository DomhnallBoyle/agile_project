using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using System;
using TestStack.White.UIItems.ListBoxItems;
namespace AutomationTests.Tests.Sprint
{
    [TestFixture]
    class ManageSprintsAsScrumMaster : BaseTestClass
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

            LoginPage.Login("user5@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            _userDashboardPage.GetProjectListItem("e2eProjectName1").Click();
            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());

            _projectDashboardPage.ManageSprintsButton.Click();

            var messageBox = MessageBoxUtil.GetInfoMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            MessageBoxUtil.ClickOKButton(messageBox);

            Assert.IsTrue(_manageSprintsPage.IsCurrentPage());

            _manageSprintsPage.CreateSprintButton.Click();
            Assert.IsTrue(_createSprintPage.IsCurrentPage());

            EnterValidCredentials();

            _createSprintPage.CreateButton.Click();

            var messageBox1 = MessageBoxUtil.GetSuccessMessageBox(MainWindow);
            Assert.NotNull(messageBox1);

            MessageBoxUtil.ClickOKButton(messageBox1);

            Assert.IsTrue(_manageSprintsPage.IsCurrentPage());
        }

        [Test]
        public void ShouldSuccessfullyDisplayProjectName()
        {
            Assert.IsTrue(_manageSprintsPage.IsCurrentPage());
            Assert.AreEqual("e2eProjectName1", _manageSprintsPage.ProjectNameTextBlock.Text);
        }

        [Test]
        public void ShouldSuccessfullyDisplaySprintInListBox()
        {
            Assert.IsTrue(_manageSprintsPage.IsCurrentPage());
            var sprintListItem = (WPFListItem)_manageSprintsPage.SprintListBox.Items.Find(item => "e2eSprintName".Equals(item.Text));
            Assert.NotNull(sprintListItem);
        }

        [Test]
        public void ShouldSuccessfullyViewCreateSprintAsScrumMaster()
        {
            Assert.IsTrue(_manageSprintsPage.IsCurrentPage());
            Assert.True(_manageSprintsPage.CreateSprintButton.Visible);
        }

        private void EnterValidCredentials()
        {
            _createSprintPage.SprintNameTextBox.Text = "e2eSprintName";
            _createSprintPage.StartDatePicker.Date = DateTime.Now.AddDays(1);
            _createSprintPage.EndDatePicker.Date = DateTime.Now.AddDays(14);
        }
    }
}
