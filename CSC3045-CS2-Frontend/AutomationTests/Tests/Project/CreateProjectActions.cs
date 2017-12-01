using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AutomationTests.Tests.Project
{
    [TestFixture]
    public class CreateProjectActions : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;
        private ProjectDashboardPage _projectDashboardPage;
        private CreateProjectPage _createProjectPage;
        [OneTimeSetUp]
        public void OneTimeSetupProjectDashboardNonManagerActions()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);
            _createProjectPage = new CreateProjectPage(MainWindow);

            LoginPage.Login("user3@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            _userDashboardPage.CreateProjectButton.Click();
            Assert.IsTrue(_createProjectPage.IsCurrentPage());

        }

        [Test]
        public void ShouldFailCreateWithInvalidProjectName()
        {
           

            _createProjectPage.ProjectNameTextBox.Text = "";
            _createProjectPage.ProjectDescriptionTextBox.Text = "";
            _createProjectPage.CreateProjectButton.Click();

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);
        }

        [Test]
        public void ShouldFailCreateWithBlankProjectName()
        {
            _createProjectPage.ProjectNameTextBox.Text = "";
           _createProjectPage.ProjectDescriptionTextBox.Text = "E2E Description";
            _createProjectPage.CreateProjectButton.Click();
            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            MessageBoxUtil.ClickOKButton(messageBox);
        }
        [Test]
        public void ShouldFailCreateWithBlankProjectDescription()
        {  
            _createProjectPage.ProjectNameTextBox.Text = "E2E Project";
            _createProjectPage.ProjectDescriptionTextBox.Text = "";
            _createProjectPage.CreateProjectButton.Click();
            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            MessageBoxUtil.ClickOKButton(messageBox);
        }
        [Test]
        public void ShouldCreateWithValidDetails()
        {
            EnterValidCredentials();
            _createProjectPage.CreateProjectButton.Click();
            var messageBox = MessageBoxUtil.GetSuccessMessageBox(MainWindow);
            Assert.NotNull(messageBox);
            MessageBoxUtil.ClickOKButton(messageBox);
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());
            _userDashboardPage.CreateProjectButton.Click();
            Assert.IsTrue(_createProjectPage.IsCurrentPage());
        }
       

        private void EnterValidCredentials()
        {
            _createProjectPage.ProjectNameTextBox.Text = "E2E Project";
            _createProjectPage.ProjectDescriptionTextBox.Text = "E2E Description";  
        }
        [Test]
        public void ClickCancelShouldReturnToUserScreen()
        {
            _createProjectPage.CancelButton.Click();
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());
            _userDashboardPage.CreateProjectButton.Click();
            Assert.IsTrue(_createProjectPage.IsCurrentPage());


        }

    }
}

