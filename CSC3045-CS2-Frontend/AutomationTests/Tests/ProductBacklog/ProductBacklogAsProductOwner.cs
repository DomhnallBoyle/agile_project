using AutomationTests.PageTemplates;
using AutomationTests.Util;
using NUnit.Framework;
using TestStack.White.UIItems.ListBoxItems;

namespace AutomationTests.Tests.ProductBacklog
{
    [TestFixture]
    class ProductBacklogAsProductOwner : BaseTestClass
    {
        
        private UserDashboardPage _userDashboardPage;
        private ProjectDashboardPage _projectDashboardPage;
        private ProductBacklogPage _productBacklogPage;
        private CreateUserStoryPage _createUserStoryPage;
        private UserStoryDetailsPage _userStoryDetailsPage;

        [OneTimeSetUp]
        public void OneTimeSetup()
        {
            _userDashboardPage = new UserDashboardPage(MainWindow);
            _projectDashboardPage = new ProjectDashboardPage(MainWindow);
            _productBacklogPage = new ProductBacklogPage(MainWindow);
            _userStoryDetailsPage = new UserStoryDetailsPage(MainWindow);
            _createUserStoryPage = new CreateUserStoryPage(MainWindow);

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
        }

        [Test]
        public void ShouldSuccessfullyDisplayUserStories()
        {
            var StoryListItem = (WPFListItem)_productBacklogPage.StoryListBox.Items.Find(item => "e2eUserStoryTest".Equals(item.Text));
            Assert.NotNull(StoryListItem);
        }

        [Test]
        public void ShouldSuccessfullyDisplayProjectName()
        {
            Assert.IsTrue(_productBacklogPage.IsCurrentPage());
            Assert.AreEqual("e2eProjectName1", _productBacklogPage.ProjectNameTextBlock.Text);
        }

        [Test]
        public void ShouldSuccessfullyDisplayCreateStoryForProductOwner()
        {
            Assert.IsTrue(_productBacklogPage.IsCurrentPage());
            Assert.True(_productBacklogPage.CreateStoryButton.Visible);
        }

        [Test]
        public void ShouldSuccessfullyAccessUserStoryDetailsForAUserStory()
        {
            Assert.IsTrue(_productBacklogPage.IsCurrentPage());
            var StoryListItem = (WPFListItem)_productBacklogPage.StoryListBox.Items.Find(item => "e2eUserStoryTest".Equals(item.Text));
            StoryListItem.Click();
            Assert.IsTrue(_userStoryDetailsPage.IsCurrentPage());
            _userStoryDetailsPage.BackButton.Click();
            Assert.IsTrue(_productBacklogPage.IsCurrentPage());
        }
    }
}
