using AutomationTests.PageTemplates;
using NUnit.Framework;
using System;

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

            LoginPage.Login("user3@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            _userDashboardPage.GetProjectListItem("e2eProjectName2").Click();
            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());

            _projectDashboardPage.ManageSprintsButton.Click();
            Assert.IsTrue(_manageSprintsPage.IsCurrentPage());

            _manageSprintsPage.CreateSprintButton.Click();
            Assert.IsTrue(_createSprintPage.IsCurrentPage());
        }

        [Test]
        public void ShouldSuccessfullyCreateASprint()
        {
            _createSprintPage.SprintNameTextBox.Text = ""
        }

        [Test]
        public void ShouldFailIfNoSprintNameEntered()
        {

        }

        [Test]
        public void ShouldFailIfNoStartDateEntered()
        {

        }

        [Test]
        public void ShouldFailIfNoEndDateEntered()
        {

        }

        [Test]
        public void ShouldFailIfAllLeftBlankWithCompundErrorMessage()
        {

        }

        [Test]
        public void ShouldFailIfStartDateIsInThePast()
        {

        }

        [Test]
        public void ShouldFailIfEndDateIsBeforeStartDate()
        {

        }

        private void ResetCreateSprintFields()
        {
            _createSprintPage.SprintNameTextBox.Text = "";
            _createSprintPage.StartDatePicker.Date = null;
            _createSprintPage.EndDatePicker.Date = null;
        }

        private void EnterValidCredentials()
        {

        }
    }
}
