using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using TestStack.White.UIItems.ListBoxItems;
namespace AutomationTests.Tests.UserStory
{
    class UserStoryDetails : BaseTestClass
    {
        private UserDashboardPage _userDashboardPage;

        private ProjectDashboardPage _projectDashboardPage;

        private ProductBacklogPage _productBacklogPage;

        private CreateUserStoryPage _createUserStoryPage;

        private UserStoryDetailsPage _userStoryDetailsPage;

        private CreateAcceptanceTestPage _createAcceptanceTestPage;

        [OneTimeSetUp]
        public void OneTimeSetup()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);
            _productBacklogPage = new ProductBacklogPage(MainWindow);
            _userStoryDetailsPage = new UserStoryDetailsPage(MainWindow);
            _createUserStoryPage = new CreateUserStoryPage(MainWindow);
            _createAcceptanceTestPage = new CreateAcceptanceTestPage(MainWindow);

            LoginPage.Login("user3@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectName1".Equals(item.Text));
            projectListItem.Click();

            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());

            _projectDashboardPage.ProductBacklogButton.Click();

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());

            _productBacklogPage.CreateStoryButton.Click();

            Assert.IsTrue(_createUserStoryPage.IsCurrentPage());

            _createUserStoryPage.enterCorrectStoryDetails("e2eUserStoryTest", "10", "e2eDescriptionTest");

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());

            var StoryListItem = (WPFListItem)_productBacklogPage.StoryListBox.Items.Find(item => "e2eUserStoryTest".Equals(item.Text));

            _productBacklogPage.CreateStoryButton.Click();

            _createUserStoryPage.enterCorrectStoryDetails("e2eUserStoryUserStoryDetails", "10", "e2eDescription");

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());


            var ProductListItem = (WPFListItem)_productBacklogPage.StoryListBox.Items.Find(item => "e2eUserStoryUserStoryDetails".Equals(item.Text));

            ProductListItem.Click();

            _userStoryDetailsPage.CreateAcceptanceTestButton.Click();

            _createAcceptanceTestPage.enterCorrectAcceptanceDetails("e2eGiven1", "e2eWhen1", "e2eThen1");

            _createAcceptanceTestPage.CreateButton.Click();

            var messageBox = MessageBoxUtil.GetSuccessMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);

        }

        [Test]
        public void ShouldSuccessfullyCheckAnAcceptanceTestExists()
        {
            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());
            var UserStoryAcceptanceTests = (WPFListItem)_userStoryDetailsPage.UserStoryAcceptanceTests.Items.Find(item => "e2eGiven1".Equals(item.Text));
        }

        // [Test]
        public void ShouldSuccessfullyCheckAcceptanceTestAsCompleted()
        {
            var UserStoryAcceptanceTests = (WPFListItem)_userStoryDetailsPage.UserStoryAcceptanceTests.Items.Find(item => "e2eGiven1".Equals(item.Text));
            _userStoryDetailsPage.CompleteLabelBlock.Select();
             Assert.IsTrue(_userStoryDetailsPage.CompleteLabelBlock.IsSelected);
        }
    }
}
