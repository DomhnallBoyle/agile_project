using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using System;
using TestStack.White.UIItems.ListBoxItems;

namespace AutomationTests.Tests.Sprints
{
    [TestFixture]
    public class CreateSprint : BaseTestClass
    {
        private string _guid;

        private CreateSprintPage _createSprintPage;
        private ManageSprintsPage _manageSprintsPage;
        private ProjectDashboardPage _projectDashboardPage;
        private UserDashboardPage _userDashboardPage;

        [OneTimeSetUp]
        public void OneTimeSetupCreateSprint()
        {
            _guid = Guid.NewGuid().ToString().Substring(0, 8);

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
        }

        [Test]
        public void ShouldSuccessfullyCreateASprint()
        {
            EnterValidCredentials();

            string sprintName = _createSprintPage.SprintNameTextBox.Text;

            _createSprintPage.CreateButton.Click();

            var messageBox = MessageBoxUtil.GetSuccessMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            MessageBoxUtil.ClickOKButton(messageBox);

            Assert.IsTrue(_manageSprintsPage.IsCurrentPage());

            WPFListItem sprintListItem = _manageSprintsPage.GetSprintListItem(sprintName);
            Assert.NotNull(sprintListItem);

            _manageSprintsPage.CreateSprintButton.Click();
        }

        [Test]
        public void ShouldFailIfNoSprintNameEntered()
        {
            EnterValidCredentials();
            _createSprintPage.SprintNameTextBox.Text = "";

            _createSprintPage.CreateButton.Click();
            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("You must enter"));

            MessageBoxUtil.ClickOKButton(messageBox);
            ResetCreateSprintFields();
        }

        [Test]
        public void ShouldFailIfNoStartDateEntered()
        {
            EnterValidCredentials();
            _createSprintPage.StartDatePicker.Date = null;

            _createSprintPage.CreateButton.Click();
            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("You must select"));

            MessageBoxUtil.ClickOKButton(messageBox);
            ResetCreateSprintFields();
        }

        [Test]
        public void ShouldFailIfNoEndDateEntered()
        {
            EnterValidCredentials();
            _createSprintPage.EndDatePicker.Date = null;

            _createSprintPage.CreateButton.Click();
            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("You must select"));

            MessageBoxUtil.ClickOKButton(messageBox);
            ResetCreateSprintFields();
        }

        [Test]
        public void ShouldFailIfAllLeftBlankWithCompoundErrorMessage()
        {
            _createSprintPage.CreateButton.Click();
            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("You must enter"));
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("You must select"));

            MessageBoxUtil.ClickOKButton(messageBox);
        }

        [Test]
        public void ShouldFailIfStartDateIsInThePast()
        {
            EnterValidCredentials();
            _createSprintPage.StartDatePicker.Date = DateTime.Now.AddDays(-7);

            _createSprintPage.CreateButton.Click();
            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("can't be in the past"));

            MessageBoxUtil.ClickOKButton(messageBox);
            ResetCreateSprintFields();
        }

        [Test]
        public void ShouldFailIfEndDateIsBeforeStartDate()
        {
            EnterValidCredentials();
            _createSprintPage.StartDatePicker.Date = DateTime.Now.AddDays(21);

            _createSprintPage.CreateButton.Click();
            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            Assert.IsTrue(MessageBoxUtil.GetTextContent(messageBox).Contains("must be after"));

            MessageBoxUtil.ClickOKButton(messageBox);
            ResetCreateSprintFields();
        }

        private void ResetCreateSprintFields()
        {
            _createSprintPage.SprintNameTextBox.Text = "";
            _createSprintPage.StartDatePicker.Date = null;
            _createSprintPage.EndDatePicker.Date = null;
        }

        private void EnterValidCredentials()
        {
            _createSprintPage.SprintNameTextBox.Text = "user3@e2e.com";
            _createSprintPage.StartDatePicker.Date = DateTime.Now;
            _createSprintPage.EndDatePicker.Date = DateTime.Now.AddDays(14);
        }
    }
}
