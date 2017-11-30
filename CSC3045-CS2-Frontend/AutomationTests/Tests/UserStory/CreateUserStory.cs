using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using TestStack.White.UIItems.ListBoxItems;

namespace AutomationTests.Tests.UserStory
{
    [TestFixture]
    class CreateUserStory : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;

        private ProjectDashboardPage _projectDashboardPage;

        private ProductBacklogPage _productBacklogPage;

        private CreateUserStoryPage _createUserStoryPage;

        [OneTimeSetUp]
        public void OneTimeSetup()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);
        }

        [Test]
        public void ShouldSuccessfullyCreateAUserStoryAsAProductOwner()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);

            LoginPage.Login("user2@e2e.com", "Aut0mation");

            var messageBox = MessageBoxUtil.GetInfoMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectName1".Equals(item.Text));
            projectListItem.Click();

            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());
            Assert.AreEqual("e2eForename2 e2eSurname2", _projectDashboardPage.ProjectManagerNameTextBlock.Text);

            _projectDashboardPage.ProductBacklogButton.Click();

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());

            _productBacklogPage.CreateStoryButton.Click();

            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());

            _createUserStoryPage.enterStoryDetails("e2eUserStory1", "10", "e2eDescription");

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());

            var StoryListItem = (WPFListItem)_productBacklogPage.StoryListBox.Items.Find(item => "e2eUserStory1".Equals(item.Text));
        }

        [Test]
        public void ShouldFailCreateAUserStoryNotAsAProductOwner()
        {

        }

    }
}
