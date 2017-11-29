using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;

namespace AutomationTests.Tests.Authentication
{
    [TestFixture]
    public class Login : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;

        [OneTimeSetUp]
        public void OneTimeSetupLogin()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
        }

        [Test]
        public void ShouldSuccessfullyLoginAsUserWithNoProjects()
        {
            LoginPage.Login("user1@e2e.com", "Aut0mation");

            var messageBox = MessageBoxUtil.GetInfoMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            _userDashboardPage.LogoutButton.Click();
        }

        [Test]
        public void ShouldSuccessfullyLoginAsUserWithProjects()
        {
            LoginPage.Login("user2@e2e.com", "Aut0mation");

            Assert.IsTrue(_userDashboardPage.IsCurrentPage());
            Assert.That(_userDashboardPage.ProjectListBox.Items.Count, Is.GreaterThan(0));

            _userDashboardPage.LogoutButton.Click();
        }

        [Test]
        public void ShouldFailLoginWithInvalidCredentials()
        {
            LoginPage.Login("BadLogin4462`[", "BadPassword33");

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            LoginPage.EmailTextBox.Text = "";
            LoginPage.PasswordTextBox.Text = "";
        }
    }
}
