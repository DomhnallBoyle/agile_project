using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;

namespace AutomationTests.Tests.UserStory
{
    [TestFixture]
    class CreateUserStory : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;

        private ProjectDashboardPage _projectDashboardPage;

        [OneTimeSetUp]
        public void OneTimeSetupLogin()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
        }

        [Test]
        public void ShouldSuccessfullyCreateAUserStoryAsAProductOwner()
        {
            LoginPage.Login("user1@e2e.com", "Aut0mation");

            var messageBox = MessageBoxUtil.GetInfoMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            //Click Project

            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());

            _projectDashboardPage.ProductBacklogButton.OnClick();
            _userDashboardPage.
            _projectDashboardPage.

        }

        [Test]
        public void ShouldFailCreateAUserStoryNotAsAProductOwner()
        {

        }

    }
}
