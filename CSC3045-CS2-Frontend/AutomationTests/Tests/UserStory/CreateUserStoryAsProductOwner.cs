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
        public void OneTimeSetupLogin()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);
            _productBacklogPage = new ProductBacklogPage(MainWindow);
            _createUserStoryPage = new CreateUserStoryPage(MainWindow);

            LoginPage.Login("user3@e2e.com", "Aut0mation");
            Assert.IsTrue(_userDashboardPage.IsCurrentPage());

            var projectListItem = (WPFListItem)_userDashboardPage.ProjectListBox.Items.Find(item => "e2eProjectName1".Equals(item.Text));
            projectListItem.Click();

            Assert.IsTrue(_projectDashboardPage.IsCurrentPage());

            _projectDashboardPage.ProductBacklogButton.Click();

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());
        }

        [Test]
        public void ShouldSuccessfullyCreateAUserStoryAsAProductOwner()
        {                  
            _productBacklogPage.CreateStoryButton.Click();

            _createUserStoryPage.enterCorrectStoryDetails("e2eUserStory1", "10", "e2eDescription");

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());

            var StoryListItem = (WPFListItem)_productBacklogPage.StoryListBox.Items.Find(item => "e2eUserStory1".Equals(item.Text));
        }

        [Test]
        public void ShouldSuccessfullyAccessCreateAUserStoryAndCancel()
        {
            _productBacklogPage.CreateStoryButton.Click();

            _createUserStoryPage.CancelButton.Click();

            Assert.IsTrue(_productBacklogPage.IsCurrentPage());
        }

        [Test]
        public void ShouldFailCreatingAUserStoryWithEmptyName()
        {
            _productBacklogPage.CreateStoryButton.Click();

            _createUserStoryPage.enterEmptyNameStoryDetails("10", "e2eDescription");

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);
        }

        [Test]
        public void ShouldFailCreatingAUserStoryWithEmptyDescription()
        {
            _productBacklogPage.CreateStoryButton.Click();

            _createUserStoryPage.enterEmptyNameStoryDetails("e2eUserStory", "10");

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);
        }

        [Test]
        public void ShouldFailCreatingAUserStoryWithEmptyMarketValue()
        { 
            _productBacklogPage.CreateStoryButton.Click();

            _createUserStoryPage.enterEmptyDescriptionStoryDetails("e2eUserStory", "e2eDesciption");

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);
        }

        [Test]
        public void ShouldFailCreatingAUserStoryWithTextMarketValue()
        {
            _productBacklogPage.CreateStoryButton.Click();

            _createUserStoryPage.enterEmptyNameStoryDetails("Ten", "e2eDescription");

            var messageBox = MessageBoxUtil.GetWarningMessageBox(MainWindow);
            Assert.NotNull(messageBox);

            MessageBoxUtil.ClickOKButton(messageBox);
        }

   
    }
}
